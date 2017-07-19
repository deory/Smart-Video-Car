


class Resource():
    __tas_parenthostname = 0
    __tas_parentport = 0
    __upload_ctname = " "
    __download_ctname = " "
    def set_tas_parenthostname(self, parenthostname):
        self.__tas_parenthostname = parenthostname

    def set_tas_parentport(self,parentport):
        self.__tas_parentport = parentport

    def set_upload_ctname(self,ctname):
        self.__upload_ctname = ctname

    def set_download_ctname(self,ctname):
        self.__download_ctname = ctname


    def get_tas_parenthostname(self):
        return self.__tas_parenthostname

    def get_tas_parentport(self):
        return self.__tas_parentport

    def get_upload_ctname(self):
        return self.__upload_ctname

    def get_download_ctname(self):
        return self.__download_ctname