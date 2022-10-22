Kind = "service-router"
Name = "backend"

Routes = [{
  Match = {
    HTTP = {
      QueryParam = [{
        Name = "canary"
        Exact = "true"
      }]
    }
  }

  Destination = {
    ServiceSubset = "v2"
  }
}]
