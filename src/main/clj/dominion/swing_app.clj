(ns dominion.swing-app
  (:require [dominion.core-rules :as dr]
            [dominion.actions :as da]
            [clojure.pprint :as pp])
  (:import (javax.swing JFrame JMenuBar JMenu JMenuItem)
           (java.awt BorderLayout)
           (java.awt.event ActionListener)
           (javax.swing.event MenuListener)))

(defn add-play-menu [menu state]
  (let [{:keys [active-player players]} @state]
    (doseq [card (get-in players [active-player :hand])]
      (.add menu (doto (JMenuItem. (name card))
                   (.addActionListener
                     (reify ActionListener
                       (actionPerformed [this e]
                         (pp/pprint (swap! state dr/execute-card card))))))))
    (.addSeparator menu)
    (.add menu (doto (JMenuItem. "Pass")
                 (.addActionListener
                   (reify ActionListener
                     (actionPerformed [this e]
                       (pp/pprint (swap! state dr/cleanup)))))))))

(defn add-buy-menu [menu state]
  (let [{:keys [supply]} @state]
    (doseq [card (keys supply)]
      (.add menu (doto (JMenuItem. (name card))
                   (.addActionListener
                     (reify ActionListener
                       (actionPerformed [this e]
                         (pp/pprint (swap! state dr/buy card))))))))))

(defn launch-app []
  (let [state (atom (dr/setup ["Mark" "Becky" "Chloe"]))]
    (doto (JFrame. "Swing App")
      (.setSize 800 600)
      (.setLayout (BorderLayout.))
      (.setJMenuBar (doto (JMenuBar.)
                      (.add (doto (JMenu. "Play")
                              (.addMenuListener
                                (reify MenuListener
                                  (menuSelected [_ event]
                                    (add-play-menu (.getSource event) state))
                                  (menuDeselected [menu event] (-> event .getSource .removeAll))))))
                      (.add (doto (JMenu. "Buy")
                              (add-buy-menu state)))))
      (.setVisible true))))
