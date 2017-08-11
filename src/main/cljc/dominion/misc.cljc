(ns dominion.misc)

;Basic Supply
{:name :curse :cost 0 :types #{ :curse }}
{:name :copper :cost 0 :money 1 :types #{ :treasure }}
{:name :silver :cost 3 :money 2 :types #{ :treasure }}
{:name :gold :cost 6 :money 3 :types #{ :treasure }}
{:name :estate :cost 2 :vp 1 :types #{ :victory }}
{:name :duchy :cost 5 :vp 3 :types #{ :victory }}
{:name :province :cost 8 :vp 6 :types #{ :victory }}

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

