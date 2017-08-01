(ns sarjis.page
  (:require
    [ajax.core :as a]
    [ajax.edn :as edn]
    [re-frame.core :as re]))

(defonce page-content (atom {}))

(defn error-handler [{:keys [status status-text]}]
  (.log js/console (str "Virhe sisällön latauksessa: " status " " status-text)))

(defn load-content [filename]
  (let [directory (re/subscribe [:directory])]
    (js/console.log "DIRECTORY:" @directory "FILE: " filename)
    (a/GET (str @directory filename ".edn")
            {:response-format (edn/edn-response-format)
            :error-handler error-handler
            :handler    (fn [response]
                          (reset! page-content response)
                          (re/dispatch [:set-active-panel :panel-content]))})))

(defn page-item [key]
  (get @page-content key))
