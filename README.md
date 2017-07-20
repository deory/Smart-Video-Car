# Smart Video Car
## Introduction
Smart Video Car is ASN(Application Service Node) of oneM2M global IoT standard.
If you want to know about oneM2M, please reference [this(oneM2M)](http://onem2m.org/technical/published-documents).
In this ASN's CSE(Common Service Entity) is consisted on [Mobius](http://developers.iotocean.org/archives/module/mobius) and AE(Application Entity) is consisted on [nCube:Thyme for Node.js](http://developers.iotocean.org/archives/module/ncube-thyme-nodejs)
You can control Smart car and watch live movie taked by the car using Android application also provied on this github.
 
## Connectivity stucture
In the smart car, Mobius and nCube:Thyme connected to each other, TAS(Thing Addapt Software) is connected to nCube:Thyme.
When user send signal make to move the smart car using application, the signal transported to mobius and nCube:Thyme and TAS. Finaly TAS make smart car's wheels to move. 
<div align="center">
<img src="https://user-images.githubusercontent.com/23092171/28393281-e88342a8-6d20-11e7-93f2-0e8a4d9742b7.png" width="600"/>
</div>

## Hardware Composition
- Raspberry Pi 3 Model B
- Smart Video Car Kit for Raspberry Pi manufactured by SUNFOUNDER

## Developement Environment
- [Raspbian OS](https://www.raspberrypi.org/downloads/raspbian/)
- [Node.js](https://nodejs.org/ko/)
- [Python](https://www.python.org)

## Installation
1. Install Raspbian OS to your Raspberry Pi 3.
2. Install Node.js to your Raspberry Pi 3.
3. Download Mobius and nCube:Thyme for Node.js to your Raspberry Pi 3.
4. Download SmartCar_TAS to your Raspberry Pi 3.
5. Configure Mobius and nCube:Thyme for Node.js setting

## Running Smart Video Car
- Run in other to Mobius, nCube:Thyme for Node.js, SmartCar_TAS with command below.
```
//for running mobius
node mobius.js

//for running nCube:Thyme for Node.js
node thyme.js

//for running SmartCar TAS
python tcp.py
```
