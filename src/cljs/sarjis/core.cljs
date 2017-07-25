(ns sarjis.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re]
            [sarjis.events]
            [sarjis.subs]
            [sarjis.views :as views]
            [sarjis.config :as config]
            [sarjis.handlers :as handlers]
            [sarjis.routes :as routes]
            [sarjis.db :as db]))

(defn splashscreen []
  (.-splashscreen js/navigator))

(defn hide-splashscreen []
  (when-let [splash (splashscreen)]
    (.hide splash)))

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export onDeviceReady []
  (js/console.log "Device Ready")
  (hide-splashscreen)
  (re/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root)
  (-> (js* "navigator")
    (.-notification)
      (.alert "Device Native Bridge works!"
      (fn [] nil)
      ""
      "")))

(defn ^:export prepare-device []
  (.addEventListener js/document "deviceready" onDeviceReady false))

(defn ^:export init []
  (db/load-menudb
    #(re/dispatch [:set-menudb %])
    #(js/alert (str "Tietokannan lataaminen epäonnistui.\n\n" (:status-text %))))
  (routes/app-routes)
  (re/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))


(js/console.log "Starting...")
; (prepare-device)
(handlers/register)
(if js/window.cordova (prepare-device) (init))
