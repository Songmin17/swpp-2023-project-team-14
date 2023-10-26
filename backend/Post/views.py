from rest_framework.views import APIView
from .models import Post,Like,Favorite,Duration,EventDuration
from rest_framework.response import Response
from .serializers import Postserializer
from django.db.models import Count, Q
from datetime import date
from rest_framework import status
# Create your views here.
# class TodayPostListView(APIView):
#   def get(self,request):
#     posts = Post.objects.filter(event_durations__event_day=date.today()).annotate(like_count=Count('like_users')).order_by('-like_count')
#     ## post와 연결된 duration 객체들 중 event_day가 오늘인 것이 하나라도 있으면 값을 반환
#     serializer = Postserializer(posts,many=True)
#     return Response(serializer.data,status=200)
class PostListView(APIView):
  # def get(self,request):
  #   posts = Post.objects.all().annotate(like_count=Count('like_users')).order_by('-like_count')
  #   serializer = Postserializer(posts,many=True)
  #   return Response(serializer.data,status=200)
  def get(self, request, keyword=None, is_festival=None, start_date=None, end_date=None):
    posts = Post.objects.all()

    if keyword:
        posts = posts.filter(title__icontains=keyword)
    if is_festival is not None:
        posts = posts.filter(is_festival=is_festival)

    if start_date and end_date:
        # start_date와 end_date가 모두 있을 경우
        posts = posts.filter(Q(event_durations__event_day__range=[start_date, end_date]))

    elif start_date:
        # start_date만 있을 경우
        posts = posts.filter(event_durations__event_day__gte=start_date)
    elif end_date:
        # end_date만 있을 경우
        posts = posts.filter(event_durations__event_day__lte=end_date)

    posts = posts.order_by('-like_count')


    serializer = Postserializer(posts, many=True)
    return Response(serializer.data, status=200)

  def post(self,request):
    title = request.data.get('title')
    content = request.data.get('content')
    author = request.user
    place = request.data.get('place')
    image = request.data.get('image')
    is_festival = request.data.get('is_festival')
    post = Post.objects.create(title=title,content=content,place=place,image=image,is_festival=is_festival,author=author)
    time = request.data.get('time')
    durations = request.data.get('duration')
    for duration_data in durations:
      event_day = duration_data.get('event_day')
      duration = Duration.objects.create(event_day=event_day)
      post.event_durations.add(duration)  # 'post'와 'duration'을 연결
      post.save() 
    serializer = Postserializer(post)
    return Response(serializer.data,status=201)
  
  
class PostDetailView(APIView):
  def get(self,request,post_id):
    post = Post.objects.get(id=post_id)
    serializer = Postserializer(post)
    return Response(serializer.data,status=200)
  
  def delete(self,request,post_id):
    post = Post.objects.get(id=post_id)
    post.delete()
    return Response(status=204)
  
# class PostFilterView(APIView):
#   def get(self,request,is_festival,date):
#     post = Post.objects.filter(is_festival=is_festival,event_durations__event_day=date)
#     serializer = Postserializer(post,many=True)
#     return Response(serializer.data,status=200)

## 여기까지 테스트 완료
# class PostBoardFilterView(APIView):
  
  
class PostFavoriteView(APIView):
  def get(self,request):
    posts = Post.objects.filter(favorite_users=request.user).annotate(like_count=Count('like_users')).order_by('-like_count')
    serializer = Postserializer(posts,many=True)
    return Response(serializer.data,status=200)
  
  
class FavoriteView(APIView):
  def patch(self, request, post_id):
        post = Post.objects.get(id=post_id)
        user = request.user
        favorite_list = post.favorite_set.filter(user=user)

        if favorite_list.count() > 0:
            # 이미 좋아요를 누른 경우, 좋아요 취소
            favorite_list.delete()
            post.favorite_count -= 1
        else:
            # 아직 좋아요를 누르지 않은 경우, 좋아요 추가
            Like.objects.create(user=user, post=post)
            post.favorite_count += 1

        post.save()

        serializer = Postserializer(post)
        return Response(serializer.data, status=200)
class LikeView(APIView):
      def patch(self, request, post_id):
        post = Post.objects.get(id=post_id)
        user = request.user
        like_list = post.like_set.filter(user=user)

        if like_list.count() > 0:
            # 이미 좋아요를 누른 경우, 좋아요 취소
            like_list.delete()
            post.like_count -= 1
        else:
            # 아직 좋아요를 누르지 않은 경우, 좋아요 추가
            Like.objects.create(user=user, post=post)
            post.like_count += 1

        post.save()

        serializer = Postserializer(post)
        return Response(serializer.data, status=200)
    