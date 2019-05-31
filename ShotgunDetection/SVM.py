import numpy as np
np.random.seed(1001)
import os
import IPython
import matplotlib
import matplotlib.pyplot as plt
import pandas as pd
import IPython.display as ipd  
import wave
from scipy.io import wavfile
rate, data = wavfile.read("molkiz.wav")
print("Sampling (frame) rate = ", rate)
print("Total samples (frames) = ", data.shape)
print(data)
plt.figure(figsize=(16, 4))
plt.plot(data[8000:9000], '.'); plt.plot(data[8000:9000], '-');
