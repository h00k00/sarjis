(ns sarjis.handlers
  (:require [re-frame.core :as re]))

(defn drawer-state [state {:keys [db]} _]
  {:db (assoc db :drawer-open state)})

(defn set-active-panel [{:keys [db]} [_ active-panel]]
  {:db (assoc db :active-panel active-panel)})

(defn set-directory [{:keys [db]} [_ directory]]
  {:db (assoc db :directory directory)})

(defn set-sub-panel [{:keys [db]} [_ sub-panel]]
  {:db (assoc db :sub-panel sub-panel)})

(defn register []
  (re/reg-event-fx :set-active-panel set-active-panel)
  (re/reg-event-fx :set-directory set-directory)
  (re/reg-event-fx :set-sub-panel set-sub-panel)
  (re/reg-event-fx :close-drawer (partial drawer-state false))
  (re/reg-event-fx :open-drawer (partial drawer-state true)))
