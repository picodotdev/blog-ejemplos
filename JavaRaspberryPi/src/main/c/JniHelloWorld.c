#include <stdio.h>
#include "io_github_picodotdev_blogbitix_javaraspberrypi_JniHelloWorld.h"

JNIEXPORT void JNICALL Java_io_github_picodotdev_blogbitix_javaraspberrypi_JniHelloWorld_print(JNIEnv *env, jobject obj)
{
    printf("Hello World!\n");
    return;
}
