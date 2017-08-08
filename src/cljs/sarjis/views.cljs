(ns sarjis.views
  (:require
    [cljsjs.material-ui]
    [cljs-react-material-ui.core :refer [get-mui-theme color]]
    [cljs-react-material-ui.reagent :as ui]
    [cljs-react-material-ui.icons :as ic]
    [secretary.core :as secretary]
    [re-frame.core :as re]
    [reagent.core :as r]
    [sarjis.db :as db]
    [sarjis.page :as page]))

(defn home-panel []
  (fn []
    [:div "This is Home Page."
     [:div "Home"]
     ]))

(defn list-items [menu-item path]
  (let [items (db/menu menu-item)]
  [:div {:style {:padding-top "80px"}}
    [ui/mui-theme-provider
        {:mui-theme (get-mui-theme {:palette {:text-color "#7A7478"}})}
        [:div
          (for [item items]
            [:div {:key (get-in item [:item])}
                [ui/flat-button {:label (get-in item [:text])
                                  :href (str path (get-in item [:item]))
                                  :style {:text-align "left"}
                                  ; :on-touch-tap
                                  ;   #(page/load-content (get-in item [:item]))
                                    }]])]]]))

(defn panel-content []
  (let [name (page/page-item :lehdennimi)
        authors (page/page-item :tarinalista)]
    (fn []
      [:div {:style {:padding-top "60px"
                     :margin "0 auto"
                     :max-width "800px"}}
        [:h1.title name]
        (for [[author id] (map-indexed (fn [i a] [a (str "author-" i)]) authors)]
          [:div {:key id}
            [:h2.author (get author :tekija)]
            (let [stories (get author :tarinat)]
              (for [[story id] (map-indexed (fn [i a] [a (str "story-" i)]) stories)]
                [:ul {:key id :style {:list-style-type "none"}}
                  [:li.story-title (str (get story :numero) " " (get story :nimike))]
                  [:li.story-original-title (get story :alkupnimi)]
                  [:li.story-publisher (get story :julk)]]
                  ))])])))

(defn albumit-panel []
  [list-items :menu/albumit "#/albumit/"])

(defn lehdet-panel []
  [list-items :menu/lehdet "#/lehdet/"])

(defn sarjat-panel []
  [list-items :menu/sarjat "#/sarjat/"])

(defn tekijat-panel []
  [list-items :menu/tekijat "#/tekijat/"])

(defmulti panels identity)
(defmethod panels :home-panel [] [home-panel])
(defmethod panels :albumit-panel [] [albumit-panel])
(defmethod panels :panel-content [] [panel-content])
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
          {:palette {:text-color "#7A7478"}})}
        [:div
          [ui/app-bar { :title "Sarjakuvia"
                        :style {:position "fixed" :text-transform "uppercase"}
                        :icon-element-right
                          (r/as-element [ui/icon-button
                            (ic/action-account-balance-wallet)])
                        :on-left-icon-button-touch-tap
                          #(re/dispatch [:open-drawer])}]
          [ui/drawer {:docked false
                      :style {:text-transform "uppercase"}
                      :open (if @drawer-open true false)
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
            (panels @active-panel)]]])))
