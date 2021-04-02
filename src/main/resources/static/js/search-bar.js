const vinylList = document.getElementById('vinylsList')
const searchBarArtist= document.getElementById('searchInputArtistTitle')
const searchBarCategory = document.getElementById('searchInputCategory')
const allVinyls = [];

fetch("http://localhost:8080/vinyls/api").then(response => response.json()).then(data => {
    for (let vinyl of data) {
        allVinyls.push(vinyl);
    }
})
console.log(allVinyls);

searchBarArtist.addEventListener('keyup', (e) => {
    const searchingCharacters = searchBarArtist.value.toLowerCase();
    let filteredVinyls = allVinyls.filter(vinyl => {
        return vinyl.artist.toLowerCase().includes(searchingCharacters)
            || vinyl.title.toLowerCase().includes(searchingCharacters);
    });
    displayAlbums(filteredVinyls);
})
searchBarCategory.addEventListener('keyup', (e) => {
    const searchingCharacters = searchBarCategory.value.toLowerCase();
    let filteredVinyls = allVinyls.filter(vinyl => {
        return vinyl.categories.toLowerCase().includes(searchingCharacters);
    });
    displayAlbums(filteredVinyls);
})

const displayAlbums = (vinyls) => {
    vinylList.innerHTML = vinyls
        .map((a) => {
            return `<div class="col mb-2"  style="width: 18rem;">
    <div class="card  box-shadow m-2 ">
        <img src="${a.imageUrl}" class="card-img-top " alt="..."  style="height: 80% width: 80% display: block;">
        <div class="card-body m-1 ">
            <div >
                <h5 class="text-dark fw-bold">Artist: ${a.artist}</h5>
                <h5 class="text-dark fw-bold">Title: ${a.title}</h5>
                <h5 class="text-dark ">${a.categories}</h5>
                <h6 class="text-dark  ">price: ${a.price}lv.</h6>
            </div>
            <div class="btn-group  justify-content-center ">
                <div >
                    <a href="/vinyls/detail/${a.id}" class="btn btn-warning active m-1"
                        aria-current="page">Details</a>
                </div>
            </div>
        </div>
    </div>
</div>`
        })
        .join('');
}


