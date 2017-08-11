(ns dominion.core-rules)

(def kingdom-cards
  [{:name :cellar :cost 2 :actions 1 :types #{ :action }}       ;discard N, draw N
   {:name :chapel :cost 2 :types #{ :action }}                  ;trash up to 4
   {:name :moat :cost 2 :cards 2 :types #{ :action :reaction }} ;stops attack
   {:name :chancellor :cost 3 :money 1 :types #{ :action }}     ;Immediately discard deck
   {:name :village :cost 3 :cards 1 :actions 2 :types #{ :action }}
   {:name :woodcutter :cost 3 :buy 1 :money 2 :types #{ :action }}
   {:name :workshop :cost 3 :types #{ :action }} ;Gain a card up to 4
   {:name :bureaucrat :cost 4 :buy 1 :money 2 :types #{ :action :attack }}
   {:name :feast :cost 4 :buy 1 :money 2 :types #{ :action }}
   {:name :gardens :cost 4 :buy 1 :money 2 :types #{ :victory }}
   {:name :militia :cost 4 :buy 1 :money 2 :types #{ :action :attack }}
   {:name :moneylender :cost 4 :buy 1 :money 2 :types #{ :action }}
   {:name :remodel :cost 4 :buy 1 :money 2 :types #{ :action }}
   {:name :smithy :cost 4 :buy 1 :money 2 :types #{ :action }}
   {:name :spy :cost 4 :buy 1 :money 2 :types #{ :action :attack }}
   {:name :thief :cost 4 :buy 1 :money 2 :types #{ :action :attack }}
   {:name :throne-room :cost 4 :buy 1 :money 2 :types #{ :action }}
   {:name :council-room :cost 5 :buy 1 :money 2 :types #{ :action }}
   {:name :festival :cost 5 :buy 1 :money 2 :types #{ :action }}
   {:name :laboratory :cost 5 :buy 1 :money 2 :types #{ :action }}
   {:name :library :cost 5 :buy 1 :money 2 :types #{ :action }}
   {:name :market :cost 5 :buy 1 :money 2 :types #{ :action }}
   {:name :mine :cost 5 :buy 1 :money 2 :types #{ :action }}
   {:name :witch :cost 5 :buy 1 :money 2 :types #{ :action :attack }}
   {:name :adventurer :cost 6 :buy 1 :money 2 :types #{ :action }}])

(defn default-supply [n]
  (into {:province 12 :duchy 12 :estate 24
         :copper 60 :silver 40 :gold 30
         :curse (* (dec n) 10)}
        (zipmap (map :name (take 10 (shuffle kingdom-cards))) (repeat 10))))

(defn initialize-game [players]
  (let [n (count players)]
    {:active-player 0
     :players (mapv (fn [player]{:name player :actions 0 :buys 0 :money 0 :hand [] :play-area [] :discard [] :deck []}) players)
     :supply (-> players count default-supply)
     :trash []}))

(defn acquire [{:keys [supply players] :as game} player card]
  (when (pos? (supply card))
    (-> game
        (update-in [:supply card] dec)
        (update-in [:players player :discard] conj card))))

(defn shuffle-discard [game player]
  (let [deck (shuffle (get-in game [:players player :discard]))]
    (-> game
        (update-in [:players player :discard] empty)
        (update-in [:players player :deck] into deck))))

(defn draw [game player]
  (if-some [p (peek (get-in game [:players player :deck]))]
    (-> game
        (update-in [:players player :deck] pop)
        (update-in [:players player :hand] conj p))
    (if (seq (get-in game [:players player :discard]))
      (draw (shuffle-discard game player) player)
      game)))

(defn draw-n
  ([game player n]
   (first (drop n (iterate #(draw % player) game))))
  ([{:keys [active-payer] :as game} n]
    (draw-n game active-payer n)))

(defn cleanup [{:keys [active-player] :as game}]
  (let [np (mod (inc active-player) (count (game :players)))]
    (-> game
        (update-in [:players active-player :discard] into (get-in game [:players active-player :play-area]))
        (update-in [:players active-player :discard] into (get-in game [:players active-player :hand]))
        (update-in [:players active-player] into {:money 0 :actions 0 :buys 0 :play-area [] :hand []})
        (draw-n active-player 5)
        (assoc :active-player np)
        (assoc-in [:players np :actions] 1)
        (assoc-in [:players np :buys] 1))))

(defn initialize-player-deck [game n]
  (draw-n
    (reduce
      (fn [game card] (acquire game n card))
      game (into (repeat 7 :copper) (repeat 3 :estate)))
    n 5))

(defn setup [players]
  (let [{:keys [players] :as game} (initialize-game players)]
    (-> (reduce initialize-player-deck game (range (count players)))
        (update-in [:players 0] into {:actions 1 :buys 1}))))

;Generic for all cards
(defmulti play (fn [_ card] card))
(defmulti cost (fn [_ card] card))

(defn execute-card
  ([game player card]
   (let [[a [c & r]] (split-with (complement #{card}) (get-in game [:players player :hand]))]
     (if c
       (-> game
           (assoc-in [:players player :hand] (reduce into [] [a r]))
           (update-in [:players player :play-area] conj c)
           (play card))
       game)))
  ([{:keys [active-player] :as game} card] (execute-card game active-player card)))

(defn +stat [stat {:keys [active-player] :as game} amount]
  (update-in game [:players active-player stat] (partial + amount)))

(def +money (partial +stat :money))
(def +buys (partial +stat :buys))
(def +actions (partial +stat :actions))
(def +cards draw-n)

(defn game-over? [{:keys [supply] :as game}]
  (or (= 3 (count (filter zero? (vals supply))))
      (zero? (supply :province))))

(defn buy
  ([game player card]
   (let [price (cost game card)]
     (cond-> game
             (and
               (pos? (get-in game [:supply card] 0))
               (pos? (get-in game [:players player :buys]))
               (>= (get-in game [:players player :money]) price))
             (->
               (acquire player card)
               (update-in [:supply card] dec)
               (update-in [:players player :buys] dec)
               (update-in [:players player :money] - price)))))
  ([{:keys [active-player] :as game } card] (buy game active-player card)))
