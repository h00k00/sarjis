(ns sarjis.db
  (:require [datascript.core :as d]
            [ajax.core :as a]
            [ajax.edn :as edn]
            [clojure.string :as string]))

(def default-db
  {:name "re-frame"
   :drawer-open false})

(def schema
  {:menu/items  {:db/cardinality :db.cardinality/many}
   :menu/name {:db/unique :db.unique/identity}})

(defonce menudb (atom {}))

(defn load-menudb [error-handler]
  (a/GET "db/menu.edn"
          {:response-format (edn/edn-response-format)
          :error-handler error-handler
          :handler       (fn [response]
                           (reset! menudb response))}))

(defn menu [key]
  (get @menudb key))
