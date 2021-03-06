class Post < ActiveRecord::Base
  belongs_to :forum_thread
  belongs_to :user
  validates :title,  presence: true, length: { maximum: 25, minimum: 3 }
  validates :text,  presence: true, length: { maximum: 400, minimum: 5 }
end
