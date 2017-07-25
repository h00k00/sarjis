(ns sarjis.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re]
            [sarjis.db :as db]))

(re/reg-sub :drawer-open (fn [{:keys [:drawer-open]}]
                                  drawer-open))
(re/reg-sub :active-panel (fn [{:keys [:active-panel]}]
                                  active-panel))
(re/reg-sub :sub-panel (fn [{:keys [:sub-panel]}]
                                  sub-panel))
(re/reg-sub :name (fn [db] (:name db)))

(re/reg-sub :items (fn [{:keys [database] :as app-db}]
                          (if-not (nil? database)
                            (db/all-items database))))
