(ns sarjis.page
  (:require
    [ajax.core :as a]
    [ajax.edn :as edn]
    [re-frame.core :as re]))

(defonce page-content (atom {}))

(defn load-content [filename]
  (let [directory (re/subscribe [:directory])]
  (js/console.log "FILE: " filename)
    (a/GET (str @directory filename ".edn")
            {:response-format (edn/edn-response-format)
            :error-handler #(js/console.log "Virhe sisällön latauksessa")
            :handler    (fn [response]
                          (reset! page-content response)
                          (re/dispatch [:set-active-panel :panel-content]))})))

(defn page-item [key]
  (get @page-content key))
