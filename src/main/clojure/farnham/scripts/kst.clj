(ns farnham.scripts.kst
    (:require [clojure.string :as str]))
(def now (java.time.ZonedDateTime/now))
(def KR-timezone (java.time.ZoneId/of "Asia/Seoul"))
(def pattern (java.time.format.DateTimeFormatter/ofPattern "HH:mm"))

(defn KR-time [time] 
    (.withZoneSameInstant time KR-timezone))

(defn parse-int [int-str]
    (Integer/parseInt int-str)
)

(defn parse-time [time] 
    (let [[hour minutes] (map parse-int (str/split time #":"))]
        (.withMinute (.withHour now hour) minutes)
    )
)

(defn show-korean-time [time]
    (.format (KR-time time) pattern)
)

(defn -main [& _args]
    (let [[time-str] _args
        time (if time-str (parse-time time-str) now)]
        (println (show-korean-time time))
    ))