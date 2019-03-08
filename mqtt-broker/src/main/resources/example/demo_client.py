import paho.mqtt.client as mqtt

def on_connect(client, userdata, flags, rc):
    print("Connected with result code "+str(rc))
    client.subscribe("/test")

def on_message(client, userdata, msg):
    print(msg.topic+" " + ":" + str(msg.payload))

client = mqtt.Client("C1")
client.on_connect = on_connect
client.on_message = on_message
client.connect("127.0.0.1", 1883, 60)

def run():
    client.loop_forever()

import time
import threading
main=threading.Thread(target = run)
main.start()
while 1:
    time.sleep(2)
    client.publish(topic="/test",payload="pppppp")