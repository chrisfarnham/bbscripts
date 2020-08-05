(ns farnham.scripts.tzs
  (:require [clojure.string :as str])
  (:require [clojure.pprint :as pp]))

(def now (java.time.ZonedDateTime/now))
(def timezones ["America/New_York" "Asia/Seoul" "Europe/Moscow"])
(def pattern (java.time.format.DateTimeFormatter/ofPattern "HH:mm"))

(defn to-zoneid [timezone]
  (java.time.ZoneId/of timezone))

(defn local-time [time tz]
  (.withZoneSameInstant time (to-zoneid tz)))

(defn fmt-time [time]
  (.format time pattern))

(defn parse-int [int-str]
  (Integer/parseInt int-str))

(defn parse-time [time]
  (let [[hour minutes] (map parse-int (str/split time #":"))]
    (.withMinute (.withHour now hour) minutes)))

; (local-time now (first timezones))
(comment (-> now (local-time (second timezones)) fmt-time ))

(defn -main [& _args]
  (let [[time-str] _args
        time (if time-str (parse-time time-str) now)
        lt-fn (fn [tz] (-> time (local-time tz) fmt-time))]
    (pp/print-table [(zipmap timezones (map lt-fn timezones))]))
)