package controllers

import javax.inject.{Inject, Singleton}

import akka.stream.scaladsl.{Source, Sink, Flow}
import com.datastax.demo.vehicle._
import com.datastax.demo.vehicle.model.Location
import com.github.davidmoten.geo.LatLong
import play.api.libs.json._
import play.api.mvc.WebSocket.MessageFlowTransformer
import play.api.mvc.{WebSocket, Action, Controller}

import scala.compat.java8.FutureConverters._


@Singleton
class PowertrainController @Inject() (vehicleDao: VehicleDao, vehicleProducer: VehicleProducer) extends Controller {


  def updateVehicleLocation(vehicle:String,lon:String, lat:String, elevation:String, speed:String, acceleration:String) = Action {
    val location: Location = new Location(new LatLong(lat.toDouble, lon.toDouble), elevation.toDouble)
    vehicleDao.updateVehicle(vehicle, location, speed.toDouble, acceleration.toDouble)
    Ok(s"updateVehicleLocation: $vehicle")
  }

  def addVehicleEvent(vehicle:String, name:String, value:String) = Action {
    vehicleDao.addVehicleEvent(vehicle,name,value)
    Ok(s"addVehicleEvent: $vehicle name:$name value:$value ")
  }

  def vehicleStream = WebSocket.accept[VehicleUpdate, String] { request =>
    val sink = Flow[VehicleUpdate].mapAsync(4) {
      case v @ VehicleLocation(vehicle, location, speed, acceleration) =>
        vehicleProducer.updateVehicle(v)
      case v @ VehicleEvent(vehicle, name, value) =>
        println(s"sending vehicle event")
        vehicleProducer.addVehicleEvent(v)
    }.to(Sink.ignore)

    Flow.fromSinkAndSource(sink, Source.maybe)
  }

  implicit val messageFlowTransformer = MessageFlowTransformer.jsonMessageFlowTransformer[VehicleUpdate, String]



}
