import tweepy
import time
from datetime import datetime
import socket
import numpy as np
from tweepy import OAuthHandler
from tweepy import Stream
from sklearn import svm
from tweepy.streaming import StreamListener
import json
from http.client import IncompleteRead
import csv
import  time
import  nltk
from dataPreprocess import NeighbourCities
from firebase_admin import db
import firebase_admin
from firebase_admin import credentials
labels1=[]
nltk.download('punkt')
from nltk.tokenize import word_tokenize
import pandas as pd 
import praw
import datetime as dt 
reddit = praw.Reddit(
  client_id='-D-YrXCm2Ae_cw',
  client_secret='0Ao2wAGARMKFhs-AntXqG_y0FQw',
  user_agent = 'hedi',
  username = 'HediBenDaoud',
  password='Kfc2EF7WtKSuTQ4'
  )
WarTextualDataset = ["shooting","terrorism","terrorist","bomber","radical","guerrila","incendiary","rebel","jihad","khalifah","kidnapper","robber","ISIS","Islamic State","The Taliban","Al-Shabaab","Al-Qaida","Boko Haram","anarchist","murderer","invader"
"adversary","agent","antagonist","archenemy","asperser","assailant","assassin","attacker","backbiter","bandit","betrayer","calumniator","contender","criminal","defamer","defiler","detractor","disputant","emulator","falsifier","foe","informer","inquisitor","invader","opponent","prosecutor","rival","saboteur","seditionist","slanderer","spy","traducer","traitor","vilifier","villain"]
compteur = 0
compteur2 = 0
labels = []
cred = credentials.Certificate("h.json")
firebase_admin.initialize_app(cred, {
    'databaseURL' : 'https://my-project-1527780396650.firebaseio.com/'
})
consumer_key = 'D0cMawgb5pUN5K7c8EfqoeMQ4'
consumer_secret = 'ZqQZVcbcOIZkVPAkTFhUbwlbXMTTMDD7wgTtBsJwNiDpw5P6ev'
access_token = '893808026691530752-fgDioq0MdS5BusO4OphCh7nrg1ogCNU'
access_secret = '0QWRpCHD79334w9fc8H4LE3dEGxnx41PWC68hun9v7yjn'
auth = OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_secret)
root = db.reference()
def FindDanger(city):
   global  compteur
   api = tweepy.API(auth)
   results = api.search(q=city)
   AllThem = ""
   for tweet in results:
     AllThem+=tweet.text
   for word in word_tokenize(AllThem):
      i=0
      for interestingWord in WarTextualDataset:
         if word == interestingWord:
               compteur+=1
               WarTextualDataset2[i]+=1
         i+=1
   compteuri=0
   ka=0
   subreddit = reddit.subreddit('Terrorism')
   for submi in subreddit.top(limit=100):
       compteuri=0
       for word in word_tokenize(submi.title):
         if (word in WarTextualDataset):
           compteuri+=1
         if(word == city):
           ka=1
   if(ka):
     compteur+=compteuri
   return compteur

i=0
print(NeighbourCities)
for city in NeighbourCities:
  print(city)
  if i==6:
    break
  labels.extend([FindDanger(city)])
  labels1.append([FindDanger(city)])
  new_city = root.child("cities").child(city).set(
       FindDanger(city)
    )
  i=i+1
X = [
   [0,0,0,0,0,0],
   [0,0,0,0,2,1],
   [0,0,0,1,1,2],
   [0,0,0,2,0,2],
   [0,0,0,4,3,2],
   [0,0,0,4,3,0],
   [0,0,0,3,4,1],
   [0,0,0,0,0,3],
   [0,0,0,2,0,3],
   [0,1,2,5,0,3],
   [0,3,0,0,0,4],
   [0,0,0,5,6,7],
   [0,0,2,1,3,4],
   [1,0,0,0,0,0],
   [1,3,4,4,5,0],
   [0,3,6,4,1,2],
   [1,9,8,10,0,30],
   [1,5,4,1,0,0],
   [1,0,0,4,5,1],
   [2,0,0,0,0,0],
   [4,7,8,9,0,1],
   [3,4,0,3,0,0],
   [2,3,0,4,5,1],
   [4,0,0,0,2,3]
]
y = [1,1,1,1,2,2,2,2,2,3,3,3,3,3,4,4,4,4,4,5,5,5,5,5]
clf = svm.SVC(probability=True)
clf.fit(X, y)
string = ""
h = np.array(labels1)
print(labels1)
final=(clf.predict_proba(h.T))
if clf.predict([labels])== 1:
   string="You're in  a safe zone"
   print(string)
elif  clf.predict([labels])== 2:
   string = "Safe but look after yourself"
   print(string)
elif clf.predict([labels])==3:
  string = "Very probable danger coming ... You should look for a safer place"
  print(string)
elif clf.predict([labels])==4:
   string = "Dangerous zone ... try to contact the army so that they rescue you"
   print(string)
elif clf.predict([labels])==5:
   string = "Terroristic attacks may occur in any moment ... Keep calm and save your life"
   print(string)
root = db.reference()
hi = root.child("SVM sentence").set(string)
new_user = root.child('Hedi').set({
    'firstOne' : final[0][0]*100,
    'secondOne' : final[0][1]*100,
    'third': final[0][2]*100,
    'fourth': final[0][3]*100,
    'fifth':final[0] [4]*100
})
comp=0
WarTextualDataset2=[0]*55
for words in WarTextualDataset:
   root.child("words").child(WarTextualDataset[comp]).set(
      WarTextualDataset2[comp]
   )
   comp+=1
