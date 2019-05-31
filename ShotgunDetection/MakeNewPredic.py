def _init_numpy_mkl():

    # Numpy+MKL on Windows only
    import os
    import ctypes
    if os.name != 'nt':
        return
    # disable Intel Fortran default console event handler
    env = 'FOR_DISABLE_CONSOLE_CTRL_HANDLER'
    if env not in os.environ:
        os.environ[env] = '1'
    # preload MKL DLLs from numpy.core
    try:
        _core = os.path.join(os.path.abspath(os.path.dirname(__file__)), 'core')
        for _dll in ('mkl_rt', 'libiomp5md', 'mkl_core', 'mkl_intel_thread',
                     'libmmd', 'libifcoremd', 'libimalloc'):
            ctypes.cdll.LoadLibrary(os.path.join(_core,_dll))
    except Exception:
        pass

_init_numpy_mkl()
del _init_numpy_mkl


