class ForumThread < ActiveRecord::Base
  has_many :posts
  belongs_to :user
  validates :title,  presence: true, length: { maximum: 25, minimum: 3 }
  validates :text,  presence: true, length: { maximum: 100, minimum: 5 }
end
