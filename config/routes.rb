Rails.application.routes.draw do
  root 'main#home'
  get 'logout' => 'users#logout'
  get 'home' => 'main#home'
  get 'login' => 'main#login'
  resources :forum_threads
  resources :homeworks
  resources :events
  resources :posts
  resources :users
end
