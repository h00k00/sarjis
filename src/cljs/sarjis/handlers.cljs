(ns sarjis.handlers
  (:require [re-frame.core :as re]))

(defn drawer-state [state {:keys [db]} _]
  {:db (assoc db :drawer-open state)})

(defn set-active-panel [{:keys [db]} [_ active-panel]]
  {:db (assoc db :active-panel active-panel)})

(defn set-sub-panel [{:keys [db]} [_ sub-panel]]
  {:db (assoc db :sub-panel sub-panel)})

(defn set-menudb [{:keys [db]} [_ database]]
  {:db (assoc db :menudb database)})

(defn register []
  (re/reg-event-fx :set-active-panel set-active-panel)
  (re/reg-event-fx :set-sub-panel set-sub-panel)
  (re/reg-event-fx :close-drawer (partial drawer-state false))
  (re/reg-event-fx :open-drawer (partial drawer-state true))
  (re/reg-event-fx :set-menudb set-menudb))
