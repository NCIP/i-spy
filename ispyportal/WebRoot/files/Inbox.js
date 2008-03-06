
function Inbox() { }
Inbox._path = '/rembrandt/dwr';

Inbox.checkStatus = function(callback) {
    DWREngine._execute(Inbox._path, 'Inbox', 'checkStatus', callback);
}

Inbox.getQueryName = function(callback) {
    DWREngine._execute(Inbox._path, 'Inbox', 'getQueryName', callback);
}

Inbox.checkSingle = function(p0, p1, callback) {
    DWREngine._execute(Inbox._path, 'Inbox', 'checkSingle', p0, p1, callback);
}

Inbox.checkAllStatus = function(p0, callback) {
    DWREngine._execute(Inbox._path, 'Inbox', 'checkAllStatus', p0, callback);
}

Inbox.deleteFinding = function(p0, callback) {
    DWREngine._execute(Inbox._path, 'Inbox', 'deleteFinding', p0, callback);
}

Inbox.mapTest = function(p0, callback) {
    DWREngine._execute(Inbox._path, 'Inbox', 'mapTest', p0, callback);
}

Inbox.getQueryDetailFromCompoundQuery = function(p0, callback) {
    DWREngine._execute(Inbox._path, 'Inbox', 'getQueryDetailFromCompoundQuery', p0, callback);
}
