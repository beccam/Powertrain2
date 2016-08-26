var bkcore = bkcore || {};
bkcore.hexgl = bkcore.hexgl || {};


bkcore.hexgl.VehicleStream = function()
{
    this.ws = new WebSocket("ws://" + window.location.host + "/vehicleStream");
    this.timer = null;

    /*this.ws.onopen = function() {
      this.ws.send("test")
    }.bind(this)*/
}

bkcore.hexgl.VehicleStream.prototype.sendEvent = function(name, value)
{
    this.ws.send(JSON.stringify({
      type: "event",
      vehicle: window.hexGL.player,
      name: name,
      value: "" + value,
      time: this.timer.time.elapsed
    }));
};

bkcore.hexgl.VehicleStream.prototype.sendLocation = function(lat, lon, elevation, speed, acceleration)
{
    console.log(this.timer.time.elapsed)
    this.ws.send(JSON.stringify({
      type: "location",
      vehicle: window.hexGL.player,
      location: {
        position: {
          lat: lat,
          lon: lon,
        },
        elevation: elevation
      },
      speed: speed,
      acceleration: acceleration,
      time: this.timer.time.elapsed

    }));
};
