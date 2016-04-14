class Post < ActiveRecord::Base
  belongs_to :forum_thread
  has_one :user
end
