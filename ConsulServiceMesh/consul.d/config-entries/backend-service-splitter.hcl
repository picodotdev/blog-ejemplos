Kind = "service-splitter"
Name = "backend"

Splits = [{
  Weight = 100
  ServiceSubset = "v1"
},
{
  Weight = 0
  ServiceSubset = "v2"
}]
