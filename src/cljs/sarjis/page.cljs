(ns sarjis.page
  (:require
    [ajax.core :as a]
    [ajax.edn :as edn]))

(defonce page-content (atom {}))

(defn load-content [filename]
  (a/GET (str "db/albumit/" filename ".edn")
          {:response-format (edn/edn-response-format)
          :error-handler #(js/console.log "Virhe sisällön latauksessa")
          :handler    (fn [response]
                        (reset! page-content response))}))

(defn page-item [key]
  (get @page-content key))
