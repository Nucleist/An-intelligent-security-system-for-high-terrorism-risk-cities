import pandas as pd
import xlrd
import numpy as np
import matplotlib as plt
NeighbourCities=[]
dict={}
df = pd.read_excel('citiestn.xlsx')
mot1 = 36.8471495
mot2 = 10.265631899999999
mot2= float(mot2)
for i in df.index:
     if (((df['Latitude'][i]-mot1)**2)+ ((df['Longitude'][i]-mot2)**2))<0.01:
       		dict[df['City'][i]]=((df['Latitude'][i]-mot1)**2)+ ((df['Longitude'][i]-mot2)**2)
       		'''if len(dict)>=5 :
       			break'''
for key, value in sorted(dict.iteritems(), key=lambda (k,v): (v,k)):
    print "%s: %s" % (key, value)
    if key[:3] != "dar":
    	NeighbourCities.append(key)
    if len(NeighbourCities)==6:
    	break
print(NeighbourCities)