vagrant up
vagrant ssh (x6)

cd ConsulServiceMesh/
consul agent -dev -config-dir=consul.d/

consul config write consul.d/config-entries/proxy-defaults.hcl
consul config write consul.d/config-entries/ingress-gateway.hcl
consul connect envoy -gateway=ingress -service ingress-gateway -admin-bind 127.0.0.1:19002 -address 127.0.0.1:20000

sudo nomad agent -dev-connect -config=nomad.d/

docker run --rm -p 9090:9090 --name prometheus --network host -v $(pwd)/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus
docker run --rm --name jaeger \
  -e COLLECTOR_ZIPKIN_HOST_PORT=:9411 \
  -e COLLECTOR_OTLP_ENABLED=true \
  -p "6831:6831/udp" \
  -p "6832:6832/udp" \
  -p "5778:5778" \
  -p "16686:16686" \
  -p "4317:4317" \
  -p "4318:4318" \
  -p "14250:14250" \
  -p "14268:14268" \
  -p "14269:14269" \
  -p "9411:9411" \
  jaegertracing/all-in-one:1.38

nomad run birds.nomad

curl -v -H "x-client-trace-id: 1" http://192.168.56.10:8080/shuffle
curl -v -H "x-client-trace-id: 1" http://192.168.56.10:8080/shuffle?canary=true

http://192.168.56.10:8500/
http://192.168.56.10:4646/
http://192.168.56.10:16686/
http://192.168.56.10:9090/
http://192.168.56.10:8080/

consul config write consul.d/config-entries/backend-service-resolver.hcl
consul config write consul.d/config-entries/backend-service-splitter.hcl
consul config write consul.d/config-entries/backend-service-router.hcl

