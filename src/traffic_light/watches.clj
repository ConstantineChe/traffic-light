(ns traffic-light.watches
  (:import [java.lang Thread])  )

(def red (atom {:count 22 :light true}))

(def yellow (atom {:count 0 :light false}))

(def green (atom {:count 10 :light false}))

(defn red-watch [key identity old new]
  (when-not (= (:count old) (:count new))
    (let [cnt (if (> (:count new) 3) (:count new) "")]
      (if (< 0 (:count new))
        (do
          (when (= 3 (:count new))
            (swap! yellow update :light not))
          (println "red: " (:light new))
          (println "yellow: " (:light @yellow) cnt)
          (println "green: " (:light @green))
          (Thread/sleep 1000)
          (swap! identity update :count dec))
        (when (:light new)
          (swap! identity update :light not)
          (swap! yellow update :light not)
          (reset! green {:count 22 :light true})
          )))))

(defn yellow-watch [key identity old new]
  (when-not (= (:count old) (:count new))
    (if (< 0 (:count new))
      (do (println "red: " (:light @red))
          (println "yellow: " (:light new))
          (println "green: " (:light @green))
          (Thread/sleep 1000)
          (swap! identity update :count dec))
      (when (:light new)
        (swap! identity update :light not)
        (reset! red {:count 22 :light true})))))

(defn green-watch [key identity old new]
  (let [cnt (:count new)]
    (when-not (= (:count old) (:count new))
      (if (< 0 (:count new))
        (do (println "red: " (:light @red))
            (println "yellow: " (:light @yellow) " " cnt)
            (println "green: " (:light new))
            (Thread/sleep 1000)
            (swap! identity update :count dec))
        (when (:light new)
          (swap! identity update :light not)
          (reset! yellow {:count 3 :light true}))))) )

(add-watch red :red red-watch)
(add-watch yellow :yellow yellow-watch)
(add-watch green :green green-watch)

;(prn @yellow)

;(swap! green (fn [_] {:light true :count 5}))
