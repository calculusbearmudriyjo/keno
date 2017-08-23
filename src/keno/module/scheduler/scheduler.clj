(ns keno.module.scheduler.scheduler
  (:require [immutant.scheduling :as scheduling]
            [keno.module.db.database :as database]
            [keno.module.engine.engine :as engine]
            ))

(defn change-state-job [] (let [[round_id] (database/get-round)]
                            (doseq [ball (engine/winner-number)]
                              (database/save-round-balls! (merge {:ball_number ball} round_id)))))

(scheduling/schedule change-state-job (scheduling/every 120 :second))
