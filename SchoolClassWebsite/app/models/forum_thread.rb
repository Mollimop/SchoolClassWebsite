class Forum_thread < ActiveRecord::Base
  has_many :posts
  has_one :user
end
