(ns sarjis.routes
  (:import goog.History)
  (:require [secretary.core :as secretary :refer-macros [defroute]]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [re-frame.core :as re]
            [sarjis.db :as db]))

  (def navigation-state
    (atom [{:name "Home" :path "/"}
           {:name "albumit" :path "/albumit"}
           {:name "lehdet" :path "/lehdet"}
           {:name "sarjat" :path "/sarjat"}
           {:name "tekijat" :path "/tekijat"}
           ]))

  (defn hook-browser-navigation! []
    (doto (History.)
      (events/listen EventType/NAVIGATE
                    (fn [event] (secretary/dispatch! (.-token event))))
      (.setEnabled true)))

  (defn app-routes []
    (secretary/set-config! :prefix "#")

    ;; --------------------
    (defroute home "/" []
      (re/dispatch [:close-drawer])
      (re/dispatch [:set-active-panel :home-panel]))

    (defroute albumit "/albumit" []
      (re/dispatch [:close-drawer])
      (re/dispatch [:set-active-panel :albumit-panel]))

    (defroute albumi "/albumit/:id" [id]
      (re/dispatch [:set-active-panel :albumi-panel])
      (re/dispatch [:set-sub-panel id]))

    (defroute lehdet "/lehdet" []
      (re/dispatch [:close-drawer])
      (re/dispatch [:set-active-panel :lehdet-panel]))

    (defroute sarjat "/sarjat" []
      (re/dispatch [:close-drawer])
      (re/dispatch [:set-active-panel :sarjat-panel]))

    (defroute tekijat "/tekijat" []
      (re/dispatch [:close-drawer])
      (re/dispatch [:set-active-panel :tekijat-panel]))
    ;; --------------------

    (hook-browser-navigation!))
