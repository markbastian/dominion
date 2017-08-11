(ns dominion.game
  (:require [dominion.core-rules :as dr]
            [dominion.actions :as da]))

(def state (atom (dr/setup ["Mark" "Becky" "Chloe"])))
(swap! state dr/execute-card :copper)
(swap! state dr/execute-card :silver)
(swap! state dr/buy :chapel)                                ;2
(swap! state dr/buy :silver)                                ;3
(swap! state dr/buy :bureaucrat)                            ;4
(swap! state dr/cleanup)

(def game
  {:active-player 0,
   :players [{:name "Mark",
              :actions 1,
              :buys 1,
              :money 4,
              :hand [:estate],
              :play-area [:copper :copper :copper :copper],
              :discard [],
              :deck [:copper :estate :estate :copper :copper]}],
   :supply {:copper 39,
            :remodel 10,
            :curse 20,
            :bureaucrat 10,
            :gardens 10,
            :laboratory 10,
            :estate 15,
            :gold 30,
            :duchy 12,
            :festival 10,
            :village 10,
            :market 10,
            :workshop 10,
            :woodcutter 10,
            :throne-room 10,
            :province 12,
            :silver 40}})

(dr/buy game :workshop)