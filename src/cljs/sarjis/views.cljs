(ns sarjis.views
  (:require
    [cljsjs.material-ui]
    [cljs-react-material-ui.core :refer [get-mui-theme color]]
    [cljs-react-material-ui.reagent :as ui]
    [cljs-react-material-ui.icons :as ic]
    [secretary.core :as secretary]
    [re-frame.core :as re]
    [reagent.core :as r]
    [sarjis.db :as db]))

(defn home-panel []
  (fn []
    [:div "This is Home Page."
     [:div "Home"]
     ]))

(defn albumit-panel []
  (let [items (db/menu :menu/albumit)]
  (fn []
    [ui/mui-theme-provider
        {:mui-theme (get-mui-theme {:palette {:text-color (color :blue200)}})}
      [:div
        (doseq [item items]
          (js/console.log (get-in item [:text]))
          [ui/flat-button {:label (get-in item [:text])
                            :href (str "#/albumit/" (get-in item [:item]))
                            :on-touch-tap
                             (fn []
                               (println "Sekalaiset A-H"))}])]])
  ; (fn []
  ;   [ui/mui-theme-provider
  ;       {:mui-theme (get-mui-theme {:palette {:text-color (color :blue200)}})}
  ;     [:div
  ;       [ui/flat-button {:label (get-in items [0 :text])
  ;                         :href (str "#/albumit/" (get-in items [0 :item]))
  ;                         :on-touch-tap
  ;                          (fn []
  ;                            (println "Sekalaiset A-H"))}]
  ;       [ui/flat-button {:label (get-in items [1 :text])
  ;                         :href "#/albumit/2"
  ;                         :on-touch-tap
  ;                          (fn []
  ;                            (println "Sekalaiset I-M"))}]
  ;       [ui/flat-button {:label "Sekalaiset N-Ö"
  ;                         :href "#/albumit/3"
  ;                         :on-touch-tap
  ;                          (fn []
  ;                            (println "Sekalaiset N-Ö"))}]
  ;       [ui/flat-button {:label "Muut"
  ;                         :href "#/albumit/4"
  ;                         :on-touch-tap
  ;                          (fn []
  ;                            (println "Muut"))}]]])
                             ))

(defn albumi-panel []
  (let [sub-panel (re/subscribe [:sub-panel])]
   (fn []
     [:div "Albumilistaus"
      [:div sub-panel]
      ])))

(defn lehdet-panel []
 (fn []
   [:div "This is the Item 3 Page."
    [:div [:a {:href "#/"} "go to Home Page"]]
    ]))

(defn sarjat-panel []
  (fn []
    [:div "This is the Item 4 Page."
     [:div [:a {:href "#/"} "go to Home Page"]]
     ]))

(defn tekijat-panel []
 (fn []
   [:div "This is the Item 5 Page."
    [:div [:a {:href "#/"} "go to Home Page"]]
    ]))

(defmulti panels identity)
(defmethod panels :home-panel [] [home-panel])
(defmethod panels :albumit-panel [] [albumit-panel])
(defmethod panels :albumi-panel [] [albumi-panel])
(defmethod panels :lehdet-panel [] [lehdet-panel])
(defmethod panels :sarjat-panel [] [sarjat-panel])
(defmethod panels :tekijat-panel [] [tekijat-panel])
(defmethod panels :default [] [home-panel])


(defn main-panel []
  (let [name (re/subscribe [:name])
        drawer-open (re/subscribe [:drawer-open])
        active-panel (re/subscribe [:active-panel])]
    (fn []
      [ui/mui-theme-provider
        {:mui-theme (get-mui-theme
          {:palette {:text-color (color :green600)}})}
        [:div
          [ui/app-bar { :title "Sarjakuvia"
                        :icon-element-right
                          (r/as-element [ui/icon-button
                            (ic/action-account-balance-wallet)])
                        :on-left-icon-button-touch-tap
                          #(re/dispatch [:open-drawer])}]
          [ui/drawer
            {:docked            false
             :open              (if @drawer-open true false)
             :on-request-change #(re/dispatch [:close-drawer])}
            [ui/app-bar { :title ""}]
            [ui/menu-item {:primary-text "Home"
                           :left-icon (ic/action-home {:color (color :grey600)})
                           :href "#/"
                           :on-touch-tap
                            (fn []
                              (println "Menu Item 1 Clicked"))}]
            [ui/divider]
            [ui/menu-item {:primary-text "Albumit"
                           :href "#/albumit"
                           :on-touch-tap
                            (fn []
                              (println "Menu Item 2 Clicked"))}]
            [ui/menu-item {:primary-text "Lehdet"
                           :href "#/lehdet"
                           :on-touch-tap
                            (fn []
                              (println "Menu Item 3 Clicked"))}]
            [ui/menu-item {:primary-text "Sarjat"
                           :href "#/sarjat"
                           :on-touch-tap
                            (fn []
                              (println "Menu Item 4 Clicked"))}]
            [ui/menu-item {:primary-text "Tekijät"
                           :href "#/tekijat"
                           :on-touch-tap
                            (fn []
                              (println "Menu Item 5 Clicked"))}]]
          [ui/paper
            (panels @active-panel)]

            ]])))
