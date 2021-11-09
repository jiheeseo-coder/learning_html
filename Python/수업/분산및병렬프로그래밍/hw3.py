from threading import Lock

def locked_method(method):
    """Method decorator. Requires a lock object at self._lock"""
    def newmethod(self, *args, **kwargs):
        with self._lock:
            return method(self, *args, **kwargs)
    return newmethod

class DecoratorLockedList(list):
    def __init__(self, *args, **kwargs):
        self._lock = Lock()
        super(DecoratorLockedList, self).__init__(*args, **kwargs)

    @locked_method
    def insert(self, *args, **kwargs):
        return super(DecoratorLockedList, self).insert(*args)

    @locked_method
    def remove(self, *args, **kwargs):
        return super(DecoratorLockedList, self).remove(*args)

l = [1,2,3,4]
c = DecoratorLockedList(l)

decorate = locked_method(c.insert(0, 5))
print(c)
decorate = locked_method(c.remove(5))
print(c)