class ForumThread < ActiveRecord::Base
  has_many :posts
  has_one :user
end
