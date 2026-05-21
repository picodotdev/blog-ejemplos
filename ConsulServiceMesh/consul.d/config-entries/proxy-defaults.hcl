Kind = "proxy-defaults"
Name = "global"

Config {
    protocol = "http"
    envoy_tracing_json = <<EOF
{
    "http":{
        "name":"envoy.tracers.zipkin",
        "typedConfig":{
            "@type":"type.googleapis.com/envoy.config.trace.v3.ZipkinConfig",
            "collector_cluster":"jaeger_collector",
            "collector_endpoint_version":"HTTP_JSON",
            "collector_endpoint":"/api/v2/spans",
            "shared_span_context":false
        }
    }
}
EOF

     envoy_extra_static_clusters_json = <<EOF
{
     "connect_timeout":"3.000s",
     "dns_lookup_family":"V4_ONLY",
     "lb_policy":"ROUND_ROBIN",
     "load_assignment": {
          "cluster_name":"jaeger_collector",
          "endpoints":[{
              "lb_endpoints" :[{
                   "endpoint": {
                        "address": {
                            "socket_address": {
                                "address":"localhost",
                                "port_value":9411,
                                "protocol":"TCP"
                           }
                       }
                  }
             }]
        }]
    },
    "name":"jaeger_collector",
    "type":"STRICT_DNS"
}
EOF
}

