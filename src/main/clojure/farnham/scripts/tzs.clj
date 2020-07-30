(ns farnham.scripts.tzs
    (:require [clojure.string :as str]))
(def now (java.time.ZonedDateTime/now))
(def KR-timezone (java.time.ZoneId/of "Asia/Seoul"))
(def timezones ["America/New_York" "Asia/Seoul" "Europe/Moscow"])
(def pattern (java.time.format.DateTimeFormatter/ofPattern "HH:mm"))

(defn to-zoneid [timezone]
  (java.time.ZoneId/of timezone))

(defn KR-time [time] 
    (.withZoneSameInstant time KR-timezone))

(defn local-time [time tz]
  (.withZoneSameInstant time (to-zoneid tz)))

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

(defn show-local-times [time]
  (map #(.format (local-time time %) pattern) timezones))

(defn -main [& _args]
  (println timezones)
  (let [[time-str] _args
        time (if time-str (parse-time time-str) now)]
    (println (show-local-times time))))