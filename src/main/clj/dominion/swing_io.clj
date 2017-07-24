(ns dominion.swing-io
  (:require [dominion.io])
  (:import (javax.swing JList JOptionPane JScrollPane)))

(defmethod dominion.io/select-cards :swing [in]
  (let [l (JList. (object-array in))]
  (JOptionPane/showMessageDialog
    nil
    (JScrollPane. l)
    "Select Value"
    JOptionPane/PLAIN_MESSAGE)
  (into [] (.getSelectedValues l))))