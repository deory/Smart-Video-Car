from xml.etree.ElementTree import parse
import Resource_repository

def Config(rs):

    rs = rs

    targetXML = open("config.xml", 'r')
    tree = parse(targetXML)
    root = tree.getroot()


    for element in root.findall("tas"):

        parenthostname = element.findtext("parenthostname")

        rs.set_tas_parenthostname(parenthostname)

        parentport = element.findtext("parentport")
        rs.set_tas_parentport(parentport)


    for element in root.findall("upload"):

        ctname1 = element.findtext("ctname")
        rs.set_upload_ctname(ctname1)

        id1 = element.findtext("id")

    for element in root.findall("download"):

        ctname2 = element.findtext("ctname")
        rs.set_download_ctname(ctname2)

        id2 = element.findtext("id")

