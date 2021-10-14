from pathlib import Path
import argparse


def main(out_dir):
    out_dir_path = Path(out_dir)
    summary = [
        '\n',
        f'### Overview',
        '\n',
        f'The Client class contains all the higher level methods',
        '\n',
        f'### Classes',
        '\n',
    ]

    for path in out_dir_path.iterdir():
        link_name = str(path.stem).replace('ai::lucidtech::las::sdk::', '')
        summary.append(f'* [{link_name}]({str(path.name)})')

    (out_dir_path / 'README.md').write_text('\n'.join(summary))


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('out_dir')
    args = parser.parse_args()

    main(**vars(args))
