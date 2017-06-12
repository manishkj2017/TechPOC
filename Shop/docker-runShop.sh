rm -rf $HOME/PetClient
docker stop ss
docker stop sg
rm -rf $HOME/PetStore
docker stop sc
docker rm ss
docker rm sc
docker rm sg
docker run --rm --name ss --net="host" -v $HOME/PetStore:/root/PetStore -d techpoc/shop-server
sleep 10
docker run --rm --name sc --net="host" -v $HOME/PetClient:/root/PetClient techpoc/shop-client > shopClient.log
docker logs ss > shopServer.log
docker run --rm --name sg --net="host" -d techpoc/shop-gui
docker logs sg > shopGUI.log