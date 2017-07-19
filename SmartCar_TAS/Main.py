from socket import *
import timer
import config
import Resource_repository
import sys


ctrl_cmd = ['forward', 'backward', 'left', 'right', 'stop', 'read cpu_temp', 'home', 'distance', 'x+', 'x-', 'y+', 'y-',
            'xy_home']

print('[nCube Tas Smart Car - Start ------------------------------------ ]')
rs = Resource_repository.Resource()
config.Config(rs)

print ('[nCube Tas Smart Car -  tas_parenthostname--------------- %s ]' % rs.get_tas_parenthostname())
print ('[nCube Tas Smart Car -  tas_parentport ------------------ %s ]' % rs.get_tas_parentport())
ADDR = (rs.get_tas_parenthostname(), int(rs.get_tas_parentport()))

tcpCliSock = socket(AF_INET, SOCK_STREAM)   # Create a socket
try:
    tcpCliSock.connect(ADDR)                    # Connect with the server
except Exception as e:
    print('Dose not Connected to (%s:%s) Thyme Server ' % ADDR)
    print('Dose not Connected to  %s ' % e.message)
    sys.exit()
print('Connected to (%s:%s) Thyme Server' % ADDR)



upload = timer.ExTimer()
upload.setHandler(timer.UploadHandler)
upload.setSock(tcpCliSock)
upload.setDelay(0.5)
upload.setData(rs)
upload.start()


down = timer.ExTimer()
down.setHandler(timer.DownloadHandler)
down.setSock(tcpCliSock)
down.setData(rs)
down.setDelay(1)
down.start()

#hoichang
tas_state = 'connect'
upload_client = socket()

upload_client.on('data', function(data))

    if tas_state == 'connect' or tas_state == 'reconnect' or tas_state == 'upload':
        def data_arr = data.toString().split('}')

        def i = 0
        for i< data_arr.length-1
            i = i + 1
            line = data_arr[i]
            line += '}';
            sink_str = util.format('%s', line.toString());
            sink_obj = JSON.parse(sink_str))













