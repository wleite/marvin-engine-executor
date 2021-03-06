/*
 * Copyright [2017] [B2W Digital]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.marvin.model

import scala.collection.mutable.Map

case class EngineMetadata(name:String,
                          version:String,
                          engineType:String,
                          actions:List[EngineActionMetadata],
                          artifactsRemotePath:String,
                          pipelineActions: List[String],
                          onlineActionTimeout:Int,
                          healthCheckTimeout:Int,
                          reloadTimeout:Int,
                          reloadStateTimeout: Option[Int],
                          batchActionTimeout:Int,
                          hdfsHost:String){
  
  override def toString: String = name

  val artifactsLocalPath:String = sys.env.getOrElse("MARVIN_DATA_PATH", "/tmp").mkString.concat( "/.artifacts")

  val actionsMap:Map[String, EngineActionMetadata] = {
    val map = Map[String, EngineActionMetadata]()
    if (actions != null) {
      for (action <- actions) {
        map += ((action.name) -> action)
      }
    }
    map
  }
}

sealed abstract class ActionTypes(val name:String) {
  override def toString:String = name
}

case object BatchType extends ActionTypes(name="batch")
case object OnlineType extends ActionTypes(name="online")

case class EngineActionMetadata(name:String, actionType:String, port:Int, host:String, artifactsToPersist:List[String], artifactsToLoad:List[String]){
  override def toString: String = name
}
