namespace java io.github.picodotdev.blogbitix.thrift

service Service {

   string ping()
   i32 add(1:i32 op1, 2:i32 op2)
   string date()
}
