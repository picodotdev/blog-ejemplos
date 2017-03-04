#include <stdio.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <wiringPi.h>

#include "io_github_picodotdev_blogbitix_javaraspberrypi_Dht11.h"

#define MAXTIMINGS 85
int dht11_dat[5] = { 0, 0, 0, 0, 0 };

JNIEXPORT void JNICALL Java_io_github_picodotdev_blogbitix_javaraspberrypi_Dht11_init(JNIEnv *env, jobject obj)
{
    wiringPiSetup();
}

JNIEXPORT void JNICALL Java_io_github_picodotdev_blogbitix_javaraspberrypi_Dht11_read(JNIEnv *env, jobject obj, jint gpio)
{
    uint8_t state	= HIGH;
	uint8_t counter	= 0;
	uint8_t j		= 0, i;

	dht11_dat[0] = dht11_dat[1] = dht11_dat[2] = dht11_dat[3] = dht11_dat[4] = 0;

	pinMode(gpio, OUTPUT);
	digitalWrite(gpio, LOW);
	delay(18);
	digitalWrite(gpio, HIGH);
	delayMicroseconds(40);
	pinMode(gpio, INPUT);

	for (i = 0; i < MAXTIMINGS; i++)
	{
		counter = 0;
		while (digitalRead(gpio) == state)
		{
			counter++;
			delayMicroseconds(1);
			if (counter == 255)
			{
				break;
			}
		}
		state = digitalRead(gpio);

		if (counter == 255)
			break;

		if ((i >= 4) && (i % 2 == 0))
		{
			dht11_dat[j / 8] <<= 1;
			if (counter > 16)
				dht11_dat[j / 8] |= 1;
			j++;
		}
	}

	if ((j >= 40) && (dht11_dat[4] == ((dht11_dat[0] + dht11_dat[1] + dht11_dat[2] + dht11_dat[3]) & 0xFF)))
	{
	    jclass clazz = (*env)->GetObjectClass(env, obj);
	    jmethodID method = (*env)->GetMethodID(env, clazz, "setTemperatureHumidity", "(II)V");
		(*env)->CallVoidMethod(env, obj, method, dht11_dat[2], dht11_dat[0]);
	}
	else {
         jclass clazz = (*env)->FindClass(env, "java/io/IOException");
         (*env)->ThrowNew(env, clazz, "Failed read");
	}

    return;
}
