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
subreddit = reddit.subreddit('Terrorism')
for submi in subreddit.top(limit=100):
	print(submi.id,submi.title)