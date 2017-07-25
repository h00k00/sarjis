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

(defn load-menudb [db-handler error handler]
  (a/GET "db/menu.edn"
          {:response-format (edn/edn-response-format)
          :error-handler error-handler
          :handler       (fn [entries]
                           (let [db (d/create-conn schema)]
                             (doseq [entry entries] (d/transact! db [entry]))
                             (db-handler db)))}))

(defn menu-items [db name]
 (->>
   (d/q '[:find ?items
          :where
          [?e :menu/id :albumit]]
        @db)
   (map (fn [[items]] [items]))
   (into {})))
