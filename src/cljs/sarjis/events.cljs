(ns sarjis.events
  (:require [re-frame.core :as re]
            [sarjis.db :as db]))

(re/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))
