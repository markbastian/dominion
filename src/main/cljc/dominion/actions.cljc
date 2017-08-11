(ns dominion.actions
  (:require [clojure.pprint :as pp]
            [dominion.core-rules :refer [execute-card +money +actions +cards +buys play cost]]))

(defn has-card?
  ([game player card]
   (some #{card} (get-in game [:players player :hand])))
  ([{:keys [active-player] :as game} card]
   (has-card? game active-player card)))

(defmethod cost :curse [game card] 0)
(defmethod cost :copper [game card] 0)
(defmethod cost :silver [game card] 3)
(defmethod cost :gold [game card] 6)
(defmethod cost :cellar [game card] 2)
(defmethod cost :chapel [game card] 2)
(defmethod cost :moat [game card] 2)
(defmethod cost :chancellor [game card] 3)
(defmethod cost :village [game card] 3)
(defmethod cost :woodcutter [game card] 3)
(defmethod cost :workshop [game card] 3)
(defmethod cost :bureaucrat [game card] 4)
(defmethod cost :feast [game card] 4)
(defmethod cost :gardens [game card] 4)
(defmethod cost :militia [game card] 4)
(defmethod cost :moneylender [game card] 4)
(defmethod cost :remodel [game card] 4)
(defmethod cost :smithy [game card] 4)
(defmethod cost :spy [game card] 4)
(defmethod cost :thief [game card] 4)
(defmethod cost :throne-room [game card] 4)
(defmethod cost :council-room [game card] 5)
(defmethod cost :festival [game card] 5)
(defmethod cost :laboratory [game card] 5)
(defmethod cost :library [game card] 5)
(defmethod cost :market [game card] 5)
(defmethod cost :mine [game card] 5)
(defmethod cost :witch [game card] 5)
(defmethod cost :adventurer [game card] 6)

;;
(defmethod play :moat [{:keys [active-player] :as game} card]
  (-> game (+cards 2)))

(defmethod play :village [{:keys [active-player] :as game} card]
  (-> game (+actions 2) (+cards 1)))

(defmethod play :woodcutter [{:keys [active-player] :as game} card]
  (-> game (+buys 1) (+money 2)))

;;Treasure cards
(defmethod play :copper [{:keys [active-player] :as game} card]
  (+money game 1))

(defmethod play :silver [{:keys [active-player] :as game} card]
  (+money game 2))

(defmethod play :gold [{:keys [active-player] :as game} card]
  (+money game 3))

(defmethod play :default [game _] game)
