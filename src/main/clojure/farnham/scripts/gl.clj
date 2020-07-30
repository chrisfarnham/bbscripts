(ns farnham.scripts.gl
    (:require [clojure.string :as str])
    (:require [babashka.curl :as curl])
    (:require [cheshire.core :as json])
    (:require [clojure.pprint :as pp])
)

(defn gl-get [url]
    (curl/get url
    {:headers {"Private-Token" (System/getenv "GITLAB_REGISTRY_TOKEN")}}))

(def gl-groups (gl-get "https://gitlab.com/api/v4/groups"))

(def gl-my-groups-ids (map :id (json/parse-string (:body gl-groups) true)))

(defn gl-projects [group-id]
    (prn (str "Getting " group-id))
    (gl-get (str "https://gitlab.com/api/v4/groups/" group-id "/projects")))

(defn gl-project-id [project]
    (->> project
        :body
         first
    )
)

(def gl-project-ids 
    (map gl-project-id 
        (map gl-projects  gl-my-groups-ids)
    )
)


(defn -main [& _args]
    ;(prn (json/parse-string (:body gl-projects)))
    ;(pp/pprint (json/parse-string (:body gl-groups)))

    (pp/pprint gl-my-groups-ids)
    ;(pp/pprint (map gl-projects gl-groups-ids))
    (pp/pprint gl-project-ids)
)